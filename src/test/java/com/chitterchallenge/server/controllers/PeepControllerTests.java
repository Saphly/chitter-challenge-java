package com.chitterchallenge.server.controllers;

import com.chitterchallenge.server.TestMongoConfig;
import com.chitterchallenge.server.model.Peep;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static com.chitterchallenge.server.helpers.JsonFileReader.peepJsonFileToObjectList;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.contains;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class PeepControllerTests {

	@Autowired
	private MockMvc mockMvc;

	private List<Peep> peeps = peepJsonFileToObjectList();

	private String requestBody;

	@BeforeEach
	void clearCollection() {
		TestMongoConfig.clearCollection("peeps");
	}

	@Nested
	@DisplayName("GET peeps tests")
	class GetAllPeepsTests {
		@Test
		@DisplayName("Should return OK Http Status code - regardless of how many peeps are found")
		void shouldReturnOkHttpStatusCode() throws Exception {
			mockMvc.perform(get("/peep/all")).andExpect(status().isOk());
		}

		@Test
		@DisplayName("Should return JSON - regardless of how many peeps are found")
		void shouldReturnJson() throws Exception {
			mockMvc.perform(get("/peep/all"))
					.andExpect(content().contentType(MediaType.APPLICATION_JSON));
		}

		@Test
		@DisplayName("Should return a ArrayList with size 0 when there are no peeps")
		void shouldReturnArrayListWithSize0() throws Exception {
			mockMvc.perform(get("/peep/all"))
					.andExpect(jsonPath("$", hasSize(0)));
		}

		@Nested
		@DisplayName("When 1 or more peeps are found")
		class WhenPeepsFound {
			@BeforeEach
			void repopulateCollection() {
				TestMongoConfig.repopulatePeepsCollection(peeps);
			}

			@Test
			@DisplayName("Should return a list with the correct length")
			void shouldReturnListOfRightLength() throws Exception {
				mockMvc.perform(get("/peep/all"))
						.andExpect(jsonPath("$", hasSize(peeps.size())));
			}

			@Test
			@DisplayName("Should return list in reverse chronological order")
			void shouldReturnListInReverseChronologicalOrder() throws Exception {
				mockMvc
						.perform(get("/peep/all"))
						.andDo(print())
						.andExpect(jsonPath("$[*].dateCreated", contains("2024-03-04T22:04:28.674Z", "2024-03-04T22:04:00.675Z")));
			}
		}
	}

	@Nested
	@DisplayName("POST Peep Tests")
	class PostPeepRequestTests {
		@Nested
		@DisplayName("New valid peep tests")
		class NewValidPeepTests {
			@BeforeEach
			void createRequestBody() {
				requestBody = "{\"username\": \"" + peeps.get(0).getUsername() +
						"\", \"name\": \"" + peeps.get(0).getName() +
						"\",\"dateCreated\": \"" + peeps.get(0).getDateCreated() + "Z" +
						"\",\"peep\": \"" + peeps.get(0).getPeep() +
						"\"}";
			}

			@Test
			@DisplayName("Should return status code 201 Created upon submitting a valid peep")
			void shouldReturnStatusCode201WhenSubmitValidPeep() throws Exception {
				mockMvc.perform(
						post("/peep/post")
						.content(requestBody)
						.contentType(MediaType.APPLICATION_JSON)
				).andExpect(status().isCreated());
			}

			@Test
			@DisplayName("Should return the valid peep that was submitted")
			void shouldReturnPeepWhenSubmitted() throws Exception {
				mockMvc.perform(
						post("/peep/post")
								.content(requestBody)
								.contentType(MediaType.APPLICATION_JSON)
				).andExpectAll(
						jsonPath("$.username", is(peeps.get(0).getUsername())),
						jsonPath("$.name", is(peeps.get(0).getName())),
						jsonPath("$.peep", is(peeps.get(0).getPeep()))
				);
			}
		}

		@Nested
		@DisplayName("Invalid POST peep request tests")
		class InvalidPostPeepRequestTests {

			@Test
			@DisplayName("Should return status code 400 when no username is provided")
			void shouldReturnStatusCode400WhenNoUsername() throws Exception {
				requestBody = "{\"name\": \"" + peeps.get(0).getName() +
						"\",\"dateCreated\": \"" + peeps.get(0).getDateCreated() + "Z" +
						"\",\"peep\": \"" + peeps.get(0).getPeep() +
						"\"}";

				mockMvc.perform(
						post("/peep/post")
								.content(requestBody)
								.contentType(MediaType.APPLICATION_JSON)
				).andExpect(status().isBadRequest());
			}

			@Test
			@DisplayName("Should return status code 400 when no name is provided")
			void shouldReturnStatusCode400WhenNoName() throws Exception {
				requestBody = "{\"username\": \"" + peeps.get(0).getUsername() +
						"\",\"dateCreated\": \"" + peeps.get(0).getDateCreated() + "Z" +
						"\",\"peep\": \"" + peeps.get(0).getPeep() +
						"\"}";

				mockMvc.perform(
						post("/peep/post")
								.content(requestBody)
								.contentType(MediaType.APPLICATION_JSON)
				).andExpect(status().isBadRequest());
			}

			@Test
			@DisplayName("Should return status code 400 when no dateCreated is provided")
			void shouldReturnStatusCode400WhenNoDateCreated() throws Exception {
				requestBody = "{\"username\": \"" + peeps.get(0).getUsername() +
						"\", \"name\": \"" + peeps.get(0).getName() +
						"\",\"peep\": \"" + peeps.get(0).getPeep() +
						"\"}";

				mockMvc.perform(
						post("/peep/post")
								.content(requestBody)
								.contentType(MediaType.APPLICATION_JSON)
				).andExpect(status().isBadRequest());
			}

			@Test
			@DisplayName("Should return status code 400 when no peep is provided")
			void shouldReturnStatusCode400WhenNoPeep() throws Exception {
				requestBody = "{\"username\": \"" + peeps.get(0).getUsername() +
						"\", \"name\": \"" + peeps.get(0).getName() +
						"\",\"dateCreated\": \"" + peeps.get(0).getDateCreated() + "Z" +
						"\"}";

				mockMvc.perform(
						post("/peep/post")
								.content(requestBody)
								.contentType(MediaType.APPLICATION_JSON)
				).andExpect(status().isBadRequest());
			}
		}
	}
}
