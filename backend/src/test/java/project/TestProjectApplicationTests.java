package project;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class TestProjectApplicationTests {

	@Test
	@DisplayName("Project class is constructible without Spring context")
	void projectClassIsConstructible() {
		assertNotNull(new ProjectApplication());
	}

}
