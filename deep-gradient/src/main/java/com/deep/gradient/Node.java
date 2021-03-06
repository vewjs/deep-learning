package com.deep.gradient;

import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import lombok.Data;

@Data
public class Node {
	private Node[] inputs;
	private String value;
	private Type type = Type.VAR;
	private Option option;
	private Double output;
	private Double gradient = 1d;
	private String name = "";

	public Node(Option option) {
		this.option = option;
		this.gradient();
	}

	public Node(Option option, Type type, String value) {
		this.option = option;
		this.type = type;
		this.value = value;
		this.gradient();
	}

	public Node(Option option, Node... inputs) {
		this.option = option;
		this.inputs = inputs;
		this.gradient();
	}

	public Node left() {
		if (inputs == null) {

			return null;

		}

		if (inputs.length > 0) {

			return inputs[0];

		}

		return null;
	}

	public Node right() {

		if (inputs == null) {

			return null;

		}

		if (inputs.length > 1) {

			return inputs[1];

		}

		return null;
	}

	private Double gradient() {

		if (Type.CONS.equals(type)) {

			return gradient = 0d;

		}

		if (Option.MINUS.equals(option)) {

			if (inputs == null) {

				return gradient = -1d;

			}

		}

		return gradient;
	}

	public void value(Map<String, Double> map) {

		if (map.get(value) != null) {

			output = map.get(value);

		} else if (value != null) {

			output = Double.valueOf(value);

		}

		if (Option.MINUS.equals(option)) {

			if (inputs == null) {

				output = -output;

			}
			
		}

	}

	public String toString() {
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		return gson.toJson(this);
	}
}
