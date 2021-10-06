#include"roster.h"
void roster::parse(string data) {
	int rhs = data.find(",");
	string studentID = data.substr(0, rhs);

	int lhs = rhs + 1;
	rhs = data.find(",", lhs);
	string firstName = data.substr(lhs, rhs - lhs);

	lhs = rhs + 1;
	rhs = data.find(",", lhs);
	string lastName = data.substr(lhs, rhs - lhs);

	lhs = rhs + 1;
	rhs = data.find(",", lhs);
	string emailAddress = data.substr(lhs, rhs - lhs);

	lhs = rhs + 1;
	rhs = data.find(",", lhs);
	int age = stoi(data.substr(lhs, rhs - lhs));

	lhs = rhs + 1;
	rhs = data.find(",", lhs);
	int daysInCourse1 = stoi(data.substr(lhs, rhs - lhs));

	lhs = rhs + 1;
	rhs = data.find(",", lhs);
	int daysInCourse2 = stoi(data.substr(lhs, rhs - lhs));

	lhs = rhs + 1;
	rhs = data.find(",", lhs);
	int daysInCourse3 = stoi(data.substr(lhs, rhs - lhs));

	DegreeProgram program = UNDECIDED;
	lhs = rhs + 1;
	if (data.at(lhs) == 'S') {
		if (data.at(lhs + 1) == 'E') program = SECURITY;
		else if (data.at(lhs + 1) == 'O') program = SOFTWARE;
	}
	else if (data.at(lhs) == 'N') program = NETWORK;


	add(studentID, firstName, lastName, emailAddress, age, daysInCourse1, daysInCourse2,
		daysInCourse3, program);
}


void roster::add(string studentID, string firstName,
	string lastName, string emailAddress,
	int age, int daysInCourse1, int daysInCourse2,
	int daysInCourse3, DegreeProgram degreeprogram){
	
	int daysInCourse[3] = { daysInCourse1 ,daysInCourse2 ,daysInCourse3 };

	rosterArray[++lastIndex] = new Student(studentID, firstName, lastName, emailAddress, age, daysInCourse, degreeprogram);
}

void roster::printAll() {
	Student::printHeader();

	for (int i = 0; i <= roster::lastIndex; i++) {
		cout << rosterArray[i]->getID() << '\t';
		cout << rosterArray[i]->getFristName() << '\t';
		cout << rosterArray[i]->getLastName() << '\t';
		cout << rosterArray[i]->getEmail() << '\t';
		cout << rosterArray[i]->getAge() << '\t';
		cout << rosterArray[i]->getDay()[0] << '\t';
		cout << rosterArray[i]->getDay()[1] << '\t';
		cout << rosterArray[i]->getDay()[2] << '\t';
		cout <<degreeProgramStrings[rosterArray[i]->getProgram()] << endl;
	}
}

void roster::printByDegreeProgram(DegreeProgram degreeprogram) {
	Student::printHeader();
	bool found = false;

	for (int i = 0; i <= roster::lastIndex; i++) {
		if (roster::rosterArray[i]->getProgram() == degreeprogram) {
			found = true;
			rosterArray[i]->print();
		}
	}
	if (found == false) {
		cout << "Invalid program Input or student has not decided yet.";
	}
	cout << endl;
}

void roster::removeById(string studentID) {
	bool found = false;
	for (int i = 0; i <= roster::lastIndex; i++) {
		if ((rosterArray[i]->getID()) == studentID) {
			found = true;
			if (i < numStudent - 1) {
				Student* temp = rosterArray[i];
				rosterArray[i] = rosterArray[numStudent - 1];
				rosterArray[numStudent - 1] = temp;
			}
			roster::lastIndex--;
		}
	}
	if (found == false) {
		cout << "Student ID is not found" << endl;
	}
}

void roster::printAverageDaysInCourse(string studentID) {
	bool found = false;
	for (int i = 0; i <= roster::lastIndex; i++) {
		if ((rosterArray[i]->getID()) == studentID) {
			found = true;
			cout << rosterArray[i]->getID() << ": ";
			int total = (rosterArray[i]->getDay()[0] + rosterArray[i]->getDay()[1] + rosterArray[i]->getDay()[2]);
			double average = total / 3.0;
			cout << average << endl;
		}
	}
	if (found == false) {
		cout << "Student ID is invalid." << endl;
	}
}

/* A valid email should include an at sign ('@') and period ('.') and should not include a space (' ').*/
void roster::printInvalidEmails() {
	bool found = false;
	for (int i = 0; i <= roster::lastIndex; i++) {
		string Temp = (rosterArray[i]->getEmail());
		if (((Temp.find('@') == string::npos) || (Temp.find('.') == string::npos)) ||(Temp.find(' ') != string::npos)) {
			found = true;
			cout << Temp << endl;
		}
		
	}
	if (!found) cout << "None" << endl;
}

	roster::~roster() {
		for (int i = 0; i < numStudent; i++) {
			delete rosterArray[i];
			rosterArray[i] = nullptr;
		}
	}
	roster::roster(){}