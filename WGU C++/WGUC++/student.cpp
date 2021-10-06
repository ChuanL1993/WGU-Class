#include "student.h"
Student::Student() {
	this->studentID = " ";
	this->lastName = " ";
	this->firstName = " ";
	this->email = " ";
	this->age = 0;
	for (int i = 0; i < dayArray; i++) this->dayInCourse[i] = 0;
	this->degreeProgram = DegreeProgram::UNDECIDED;
}
Student::Student(
	string studentID,
	string firstName,
	string lastName,
	string email,
	int age,
	int dayInCourse[],
	DegreeProgram degreeProgram){
	this->studentID = studentID;
	this->lastName = lastName;
	this->firstName = firstName;
	this->email = email;
	this->age = age;
	for (int i = 0; i < dayArray; i++) this->dayInCourse[i] = dayInCourse[i];
	this->degreeProgram = degreeProgram;
}
Student::~Student(){}

string Student::getID() { return this->studentID; }
string Student::getFristName() { return this->firstName; }
string Student::getLastName() { return this->lastName; }
string Student::getEmail() { return this->email; }
int Student::getAge() { return this->age; }
int* Student::getDay() { return this->dayInCourse; }
DegreeProgram Student::getProgram() { return this->degreeProgram;}

void Student::setID(string ID) { this->studentID = ID; }
void Student::setFirstName(string firstName) { this->firstName = firstName; }
void Student::setLastName(string lastName) { this->lastName = lastName; }
void Student::setEmail(string email) { this->email = email; }
void Student::setAge(int age) { this->age = age; }
void Student::setDay(int dayInCourse[]){
	for (int i = 0; i < dayArray; i++)
		this->dayInCourse[i] = dayInCourse[i];

}
void Student::setProgram(DegreeProgram Program) { this->degreeProgram = Program; }

void Student::printHeader() {
	cout << "Output format: StudentID|FirstName|LastName|Email|Age|DaysInCourse|DegreeProgram" << endl;
}

void Student::print() {
	cout << this->getID() << '\t';
	cout << this->getFristName() << '\t';
	cout << this->getLastName() << '\t';
	cout << this->getEmail() << '\t';
	cout << this->getAge() << '\t';
	cout << this->getDay()[0] << ',' << this->getDay()[1] << ',' << this->getDay()[2] << '\t';
	cout << degreeProgramStrings[this->getProgram()] << endl;
}