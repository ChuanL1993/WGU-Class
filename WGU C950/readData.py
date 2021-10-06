import csv
from Hashmap import HashTable
import datetime
from Package import Package


#space and time complexity: O(N+N^2+N^2+N^2) ->O(N^2)
with open('./packageDetail.csv') as packageDetail:
    # set the first truck start time as 8:0:0(Date infor is not used here).
    first_truck_ST = datetime.datetime(1, 1, 1, 8, 0, 0)

    # set the second truck start time as 8:0:0(Date infor is not used here).
    second_truck_ST = datetime.datetime(1, 1, 1, 9, 5, 0)

    # set the third truck start time as 8:0:0(Date infor is not used here).
    third_truck_ST = datetime.datetime(1, 1, 1, 12, 0, 0)

    # empty dictionary for each truck, package loaded into truck is unsorted, and order is random
    first_truck = {}
    second_truck = {}
    third_truck = {}

    hashTable = HashTable()

    # start reading package data
    # space and time complexity: O(N)
    packageData = csv.reader(packageDetail, delimiter=',')
    for row in packageData:
        pID = int(row[0])
        pAddress = row[1]
        pCity = row[2]
        pState = row[3]
        pZip = row[4]
        pDeadline = row[5]
        pMass = row[6]
        pNote = row[7]
        pStatus = "In the hub"

        # create instance of package
        p = Package(pID, pAddress, pCity, pState, pZip, pDeadline, pMass, pNote, pStatus)

        # if package meet condition requirement,  it would be add into truck and hashtable.
        if len(second_truck) < 16:
            if "truck 2" in pNote:
                second_truck[pID] = pAddress
                p.set_leave_hub(second_truck_ST)
                hashTable.insert(pID, p)
            if "Delay" in pNote:
                second_truck[pID] = pAddress
                p.set_leave_hub(second_truck_ST)
                hashTable.insert(pID, p)

        # if package meet condition requirement, add leave_hub time for the package and
        # it would be added into truck and hashtable.
        if len(first_truck) < 16:
            if "Must" in pNote:
                first_truck[pID] = pAddress
                p.set_leave_hub(first_truck_ST)
                hashTable.insert(pID, p)
            if pID in (13, 19, 15):
                first_truck[pID] = pAddress
                p.set_leave_hub(first_truck_ST)
                hashTable.insert(pID, p)

        # if package meet condition requirement, add leave_hub time for the package and
        # it would be added into truck and hashtable.
        if "Wrong" in pNote:
            third_truck[pID] = pAddress
            p.set_leave_hub(third_truck_ST)
            hashTable.insert(pID, p)

        # everything else is added into hashtable
        else:
            hashTable.insert(pID, p)

    # go through hashtable and find package with same address but not loaded.
    # space and time complexity: O(N*(N+N)) ->O(N^3)
    for i in range(1, 41):
        if len(second_truck) < 16:
            p = (hashTable.search(i))
            if p.ID not in second_truck.keys() and p.address in second_truck.values():
                second_truck[p.ID] = p.address
                p.set_leave_hub(second_truck_ST)

    # go through hashtable and find package with deadline requirement.
    # space and time complexity: O(N*(N+N+N)) ->O(N^2）
    for i in range(1, 41):
        if len(first_truck) < 16:
            p = (hashTable.search(i))
            if p.deadline != "EOD" and p.ID not in second_truck.keys() and p.ID not in first_truck.keys() and p.ID not \
                    in third_truck.keys():
                first_truck[p.ID] = p.address
                p.set_leave_hub(first_truck_ST)

    # go through hashtable and find package with same address but not loaded.
    # space and time complexity: O(N*(N+N+N+N)) ->(N^2)
    for i in range(1, 41):
        if len(first_truck) < 16:
            p = (hashTable.search(i))
            if p.ID not in first_truck.keys() and p.ID not in third_truck.keys() and p.ID not in second_truck.keys()\
                    and p.address in first_truck.values():
                first_truck[p.ID] = p.address
                p.set_leave_hub(first_truck_ST)

    # go through hashtable and find package with same address but not loaded.
    # space and time complexity: O(N*(N+N) -> O(N^2)
    for i in range(1, 41):
        p = (hashTable.search(i))
        if p.ID not in first_truck.keys() and p.ID not in second_truck.keys():
            third_truck[p.ID] = p.address
            p.set_leave_hub(third_truck_ST)


# method to change the wrong address in hashtable
# space and time complexity: O(1)
def correction(myhash):
    corrected = Package('9', "410 S State St", "Salt Lake City", "UT", "84111", "EOD", "2", "Wrong address listed(corrected)",
                              "In the Hub")
    corrected.leave_hub = third_truck_ST
    myhash.insert(9, corrected)
    return myhash


# update the third truck, the one with wrong address in loaded in the third truck
# space and time complexity: O(N)
def update_third_truck(third_truck):
    for x in third_truck.keys():
        if x == 9:
            third_truck[x] = "410 S State St"
    return third_truck


# return hashtable
# space and time complexity: O(1)
def getHashTable():
    return hashTable


# return second_truck
# space and time complexity: O(1)
def get_second_truck():
    return second_truck


# return first_truck
# space and time complexity: O(1)
def get_first_truck():
    return first_truck


# return third_truck
# space and time complexity: O(1)
def get_third_truck():
    return third_truck
