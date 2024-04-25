package com.djawnstj.mvcframework.boot;

import java.util.*;

public class ApplicationArguments {

	private final Source source;

    public ApplicationArguments(final String... args) {
        this.source = new Source(parse(args));
    }

	private Map<String, List<String>> parse(final String... args) {
		if (args.length == 0) {
			return Collections.unmodifiableMap(new LinkedHashMap<>());
		}

		final Map<String, List<String>> argsMap = new LinkedHashMap<>();

		Arrays.stream(args)
				.forEach(arg -> {
					final String[] argKeyValue = arg.split("=");

					if (argKeyValue.length != 2) {
						throw new IllegalArgumentException("wrong args - '" + arg + "'");
					}
					argsMap.computeIfAbsent(argKeyValue[0], s -> new ArrayList<>()).add(argKeyValue[1]);
				});

		return Collections.unmodifiableMap(argsMap);
	}

	public Set<String> getOptionNames() {
		return this.source.getOptionNames();
	}

	public List<String> getOptionValues(final String name) {
		return this.source.getOptionValues(name);
	}

    private static class Source {

		private final Map<String, List<String>> argsMap;

		public Source(final Map<String, List<String>> argsMap) {
			this.argsMap = argsMap;
		}

		public Set<String> getOptionNames() {
			return this.argsMap.keySet();
		}

   		public List<String> getOptionValues(final String name) {
			final List<String> originArgValues = this.argsMap.get(name);

			return (originArgValues != null) ? Collections.unmodifiableList(originArgValues)
					: Collections.emptyList();
   		}

   	}
}
