#pragma once
#include<iostream>
#include<iomanip>
#include "degree.h"
using std::string;
using std::cout;
using std::endl;
//using namespace std;

class Student
{
public:
	const static int dayArray = 4;
	Student();//default constructor
	Student(
		string studentID,
		string firstName,
		string lastName,
		string email,
		int age,
		int dayInCourse[],
		DegreeProgram degreeProgram);
	//constructor with paramater
	~Student();//destructor
	/*
	Below are some getter and mutators for the class
	*/
	//getter fuction:
	string getID();
	string getFristName();
	string getLastName();
	string getEmail();
	int getAge();
	int* getDay();
	DegreeProgram getProgram();

	//mutator fuction:
	void setID(string ID);
	void setFirstName(string firstName);
	void setLastName(string lastName);
	void setEmail(string email);
	void setAge(int age);
	void setDay(int dayInCourse[]);
	void setProgram(DegreeProgram Program);

	static void printHeader();

	void print();

private:
	string studentID;
	string firstName;
	string lastName;
	string email;
	int age;
	int dayInCourse[dayArray];
	DegreeProgram degreeProgram;

};


