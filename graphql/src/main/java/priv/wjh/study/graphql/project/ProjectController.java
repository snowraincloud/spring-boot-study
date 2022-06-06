/*
 * Copyright 2002-2021 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package priv.wjh.study.graphql.project;

import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class ProjectController {

	private final SpringProjectsClient client;

	public ProjectController(SpringProjectsClient client) {
		this.client = client;
	}

	@QueryMapping
	public Project project(@Argument String slug) {
		return client.fetchProject(slug);
	}

	@SchemaMapping
	public List<Release> releases(Project project) {
		return client.fetchProjectReleases(project.getSlug());
	}

}
