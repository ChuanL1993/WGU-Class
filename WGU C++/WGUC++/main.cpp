#include"roster.h"
int main() {
	cout << "Scripting and Programming - Applications ¨C C867" << endl;
	cout << "This program is running with C++" << endl;
	cout << "Student ID:#001444122" << endl;
	cout << "Name:Chuan Liu" << endl;


	const string studentData[] = { "A1,John,Smith,John1989@gm ail.com,20,30,35,40,SECURITY",
		"A2,Suzan,Erickson,Erickson_1990@gmailcom,19,50,30,40,NETWORK",
		"A3,Jack,Napoli,The_lawyer99yahoo.com,19,20,40,33,SOFTWARE",
		"A4,Erin,Black,Erin.black@comcast.net,22,50,58,40,SECURITY",
		"A5,Chuan,Liu,cliu11@wgu.edu,27,7,18,12,SOFTWARE"};
	const static int numStudent = 5;
	roster classRoster;
	for (int i = 0; i < numStudent; i++) {
		classRoster.parse(studentData[i]);
	}
	cout << "Here is the list of all student:" << endl;
	classRoster.printAll();
	cout << endl;
	
	cout << "Here is the list of invalid Email address:" << endl;
	classRoster.printInvalidEmails();
	cout << endl;

	cout << "Here is average time for this student for his/her 3 courses:" << endl;
	classRoster.printAverageDaysInCourse("A5");
	cout << endl;

	cout << "Here is the list of student with Software program: " << endl;
	classRoster.printByDegreeProgram(SOFTWARE);
	cout << endl;

	cout << "Removing student A3 from list..." << endl;
	classRoster.removeById("A3");
	cout << endl;

	cout << "Here is the list of all student:" << endl;
	classRoster.printAll();
	cout << endl;

	cout << "Removing student A3 from list..." << endl;
	classRoster.removeById("A3");
	cout << endl;

	system("pause");
	return 0;

}