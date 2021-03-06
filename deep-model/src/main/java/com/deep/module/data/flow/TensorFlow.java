package com.deep.module.data.flow;

import com.deep.gradient.Option;
import com.deep.math.Matrix;
import com.deep.module.gradient.Gradient;
import com.deep.module.graph.Node;
import com.deep.module.graph.Shape;
import com.deep.module.graph.Shapes;
import com.deep.util.Array;

public class TensorFlow<E> extends Shapes {

	private Gradient grad;
	public final Array<Node> list;

	public TensorFlow() {

		list = new Array<Node>();

		grad = new Gradient();

	}

	public Node prod(Shape... shapes) {

		Node node = new Node(Option.PROD, shapes) {

			public void compute() {

				Shape<double[][]> weight = shapes[0];
				Shape<double[][][][]> input = shapes[1];

				output(Matrix.prod(weight.get(), input.get()));

			}

			public void gradient() {

				Shape<E[]> output = output();
				output().diff(reshape(output.diff(), Shape.fill(output.get(), 0)));

				grad.prod(shapes[0], shapes[1], output());

			}

		};

		list.add(node);

		return node;

	}

	public Node matmul(Shape... shapes) {

		Node node = new Node(Option.MATMUL, shapes) {

			public void compute() {

				Shape<double[][]> weight = shapes[0];
				Shape<E[]> input = shapes[1];
				input.reshape(new double[input.get().length][1]);

				output(Matrix.mult(weight.get(), (double[][]) shapes[1].get()));

			}

			public void gradient() {

				grad.matmul(shapes[0], shapes[1], output());
				shapes[1].shape();

			}

		};

		list.add(node);

		return node;

	}

	public Node add(Shape<double[][]>... shapes) {

		Node node = new Node(Option.ADD, shapes) {

			public void compute() {

				output(Matrix.add(shapes[0].get(), shapes[1].get()));

			}

			public void gradient() {

				grad.add(shapes[0], shapes[1], output());

			}

		};

		list.add(node);

		return node;

	}

	public Node add3(Shape... shapes) {

		Node node = new Node(Option.ADD3, shapes) {

			public void compute() {

				Shape<double[][][]> input = shapes[0];
				Shape<double[]> bias = shapes[1];
				output(Matrix.add(input.get(), bias.get()));

			}

			public void gradient() {

				grad.add3(shapes[0], shapes[1], output());

			}

		};

		list.add(node);

		return node;

	}

	public Node sigmoid(Shape<double[][]>... shapes) {

		Node node = new Node(Option.SIGMOID, shapes) {

			public void compute() {

				output(Matrix.sigmoid(shapes[0].get()));

			}

			public void gradient() {

				grad.sigmoid(shapes[0], output());

			}

		};

		list.add(node);

		return node;

	}

	public Node reduce(Shape... shapes) {

		Node node = new Node(Option.REDUCE, shapes) {

			public void compute() {

				output(shapes[1]);

			}

			public void gradient() {

				grad.reduce(shapes);

			}

		};

		list.add(node);

		return node;

	}

	public Node conv(Shape<double[][][]>... shapes) {

		Node node = new Node(Option.CONV, shapes) {

			public void compute() {

				output(Matrix.conv(shapes[0].get(), shapes[1].get()));

			}

			public void gradient() {

				grad.conv(shapes[0], shapes[1], output());

			}

		};

		list.add(node);

		return node;

	}

	public Node relu(Shape<double[][][]>... shapes) {

		Node node = new Node(Option.RELU, shapes) {

			public void compute() {

				output(Matrix.relu(shapes[0].get()));

			}

			public void gradient() {

				grad.relu(shapes[0], output());

			}

		};

		list.add(node);

		return node;

	}

	public Node maxpool(Shape<double[][][]>... shapes) {

		Node node = new Node(Option.MAXPOOL, shapes) {

			public void compute() {

				output(Matrix.maxpool(shapes[0].get()));

			}

			public void gradient() {

				grad.maxpool(shapes[0], output());

			}

		};

		list.add(node);

		return node;

	}

	public Node softmax(Shape<double[][]>... shapes) {

		Node node = new Node(Option.SOFTMAX, shapes) {

			public void compute() {

				output(Matrix.softmax(shapes[0].get()));

			}

			public void gradient() {

				grad.softmax(shapes[0], output());

			}

		};

		list.add(node);

		return node;

	}

}
