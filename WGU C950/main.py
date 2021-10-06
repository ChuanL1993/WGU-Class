# C950 - Data Structures and Algorithms II
# Chuan Liu
# WGU student ID: 001444122
from readData import getHashTable
from readData import get_second_truck
from readData import get_first_truck
from readData import get_third_truck
from readData import update_third_truck
from distance import sort_package_by_distance
import datetime
from delivery_time import update_time
from readData import correction


class Main:
    # set the first truck start time as 8:0:0(Date infor is not used here).
    first_truck_ST = datetime.datetime(1, 1, 1, 8, 0, 0)

    # set the second truck start time as 8:0:0(Date infor is not used here).
    second_truck_ST = datetime.datetime(1, 1, 1, 9, 5, 0)

    # set the third truck start time as 8:0:0(Date infor is not used here).
    third_truck_ST = datetime.datetime(1, 1, 1, 12, 0, 0)

    # put data into hashtable
    myHash = getHashTable()

    # Get the package loaded in the truck for truck 1, 2, 3.
    second_truck = get_second_truck()
    first_truck = get_first_truck()
    third_truck = get_third_truck()

    # correct the information for package on truck and hashtable.
    myHash = correction(myHash)
    third_truck = update_third_truck(third_truck)

    # call sort_package_by_distance method to put the package in desired order.
    sorted_first_truck = sort_package_by_distance(first_truck)
    sorted_second_truck = sort_package_by_distance(second_truck)
    sorted_third_truck = sort_package_by_distance(third_truck)

    # update the delivered time for each package on each truck.
    myHash = update_time(myHash, sorted_first_truck, first_truck_ST)
    myHash = update_time(myHash, sorted_second_truck, second_truck_ST)
    myHash = update_time(myHash, sorted_third_truck, third_truck_ST)

    # Get the mile form truck 1, 2, 3 and add them up to get total miles.
    # space and time complexity: O(N+N+N) ->O(N)
    truck_first_mile = 0
    truck_second_mile = 0
    truck_third_mile = 0
    for x in sorted_first_truck.values():
        truck_first_mile += x[1]
    for x in sorted_second_truck.values():
        truck_second_mile += x[1]
    for x in sorted_third_truck.values():
        truck_third_mile += x[1]
    total_mile = truck_first_mile + truck_second_mile + truck_third_mile

    # interface to communicate with users
    print("Welcome to WGUPS pack-tracking system!")
    print("--------------------------------------")
    print(f"Current route complete in {total_mile:.2f} miles")
    print("--------------------------------------")
    input_1 = input("Please select the behavior you would like to perform or type 'quit' to exit:\n"
                  "1)Look up an package at a specific time.\n"
                  "2)Look up all package at a specific time.\n")
    if input_1 != 'quit':
        if input_1 == '1':
            input_id = input("Please provide package id:")
            input_time = input("Please type in  a time in HH:MM:SS format:")
            (h, m, s) = input_time.split(':')

            # to set the day as same as truck time and delivered time and (0001-01-01) to do a comparison.
            user_time = datetime.datetime(1, 1, 1, int(h), int(m), int(s))
            package = myHash.search(int(input_id))
            print(package.ID, package.address, package.city, package.state, package.zip, package.mass, package.note,
                  sep='|', end='|')

            # package default status is in the hub.
            if user_time <= package.leave_hub:
                print(package.status)

            # instead of default status, print "en route".
            elif package.leave_hub < user_time < package.delivered:
                print("en route")

            # print the time when the package is delivered.
            elif user_time >= package.delivered:
                print("Delivered at " + package.delivered.strftime("%X"))
            else:
                print("There is something wrong with this package.")

        elif input_1 == '2':
            input_time = input("Please type in  a time in HH:MM:SS format:")
            (h, m, s) = input_time.split(':')

            # to set the day as same as truck time and delivered time and (0001-01-01) to do a comparison.
            # space and time complexity: O(N)
            user_time = datetime.datetime(1, 1, 1, int(h), int(m), int(s))
            for x in range(1, 41):
                package = myHash.search(x)
                print(package.ID, package.address, package.city, package.state, package.zip, package.mass, package.note, sep='|', end='|')

                # package default status is in the hub.
                if user_time <= package.leave_hub:
                    print(package.status)

                # instead of default status, print "en route".
                elif package.leave_hub < user_time < package.delivered:
                    print("en route")

                # print the time when the package is delivered.
                elif user_time >= package.delivered:
                    print("Delivered at " + package.delivered.strftime("%X"))
                else:
                    print("There is something wrong with this package.")
        else:
            print("Invalid input")
            exit(0)
    else:
        exit(0)


