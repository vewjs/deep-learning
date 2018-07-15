package com.deep.module.graph;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import com.deep.gradient.Option;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public abstract class Node {

	Option option;

	Shape ouput;

	Map<String, Shape> map;

	public abstract void compute();

	public abstract void gradient();

	public Node(Option option, Shape... shapes) {

		this.option = option;

		this.ouput = new Shape("output");

		this.map = new HashMap<String, Shape>();

		Arrays.stream(shapes).forEach(shape -> {

			map.put(shape.getName(), shape);

		});

	}

	public Shape get(String name) {

		return map.get(name);

	}

	public <E> void output(E data) {

		ouput.set(data);

	}

	public Shape output() {

		return ouput;

	}

	public String toString() {
		Gson gson = new GsonBuilder().create();
		StringBuilder builder = new StringBuilder();
		map.forEach((key, value) -> {
			builder.append(gson.toJson(value));
			builder.append("\n       ");
		});
		builder.append(gson.toJson(ouput));
		return builder.toString();
	}

}
