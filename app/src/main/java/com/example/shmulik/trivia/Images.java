package com.example.shmulik.trivia;

import java.io.Serializable;

public class Images implements Serializable{


	private String name;
	private String optionOne;
	private String optionTwo;
	private String optionThree;
	private String optionFour;
	private String url;


    public Images()
    {

    }

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getOptionOne() {
		return optionOne;
	}

	public void setOptionOne(String optionOne) {
		this.optionOne = optionOne;
	}

	public String getOptionTwo() {
		return optionTwo;
	}

	public void setOptionTwo(String optionTwo) {
		this.optionTwo = optionTwo;
	}

	public String getOptionThree() {
		return optionThree;
	}

	public void setOptionThree(String optionThree) {
		this.optionThree = optionThree;
	}

	public String getOptionFour() {
		return optionFour;
	}

	public void setOptionFour(String optionFour) {
		this.optionFour = optionFour;
	}
	
	public String getUrl(){return url;}

	public void setUrl(String url) {
		this.url = url;
	}
}
