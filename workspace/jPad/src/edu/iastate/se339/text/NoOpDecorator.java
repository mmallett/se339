package edu.iastate.se339.text;

public class NoOpDecorator extends AbstractDecorator{

	public NoOpDecorator(AbstractRepresentation component) {
		super(component);
	}

	@Override
	public String toString() {
		return component.toString();
	}

}
