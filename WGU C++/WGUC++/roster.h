#pragma once
#include"student.h"
class roster
{
public:
	roster();
	~roster();
	void parse(string data);
	void add(
		string studentID, string firstName,
		string lastName, string emailAddress,
		int age, int daysInCourse1, int daysInCourse2,
		int daysInCourse3, DegreeProgram degreeprogram);
	void printAll();
	void printByDegreeProgram(DegreeProgram degreeProgram);
	void removeById(string studentID);
	void printAverageDaysInCourse(string studentID);
	void printInvalidEmails();
	// A valid email should include an at sign ('@') and period ('.') and should not include a space (' ').
private:
	int lastIndex = -1;
	const static int numStudent = 5;
	Student* rosterArray[numStudent];
	
	
};

