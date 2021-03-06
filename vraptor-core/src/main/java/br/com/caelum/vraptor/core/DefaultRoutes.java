/***
 * Copyright (c) 2009 Caelum - www.caelum.com.br/opensource
 * All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package br.com.caelum.vraptor.core;

import java.util.EnumSet;

import br.com.caelum.vraptor.http.route.Router;
import br.com.caelum.vraptor.ioc.ApplicationScoped;
import br.com.caelum.vraptor.ioc.Component;
import br.com.caelum.vraptor.proxy.MethodInvocation;
import br.com.caelum.vraptor.proxy.Proxifier;
import br.com.caelum.vraptor.resource.HttpMethod;

/**
 * Default implementation of route info extractor.
 * @author guilherme silveira
 * @since 3.0.3
 */
@Component
@ApplicationScoped
public class DefaultRoutes implements Routes{

	private final Proxifier proxifier;
	private final Router router;

	public DefaultRoutes(Router router, Proxifier proxifier) {
		this.router = router;
		this.proxifier = proxifier;
	}

	private String uri;

	public <T> T uriFor(final Class<T> type) {
		return proxifier.proxify(type, new MethodInvocation<T>() {
			public Object intercept(T proxy, java.lang.reflect.Method method,
					Object[] args, br.com.caelum.vraptor.proxy.SuperMethod superMethod) {
				uri = router.urlFor(type, method, args);
				return null;
			};
		});
	}

	public String getUri() {
		return uri;
	}

	public EnumSet<HttpMethod> allowedMethodsFor(String uri) {
		return router.allowedMethodsFor(uri);
	}

}
