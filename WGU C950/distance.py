import csv

# This open the address file and store into address_name
with open("./address_index.csv") as address_name_index:
    address_name = csv.reader(address_name_index, delimiter=',')
    address_name = list(address_name)

# This open the distance file and store into distance
with open("./distance.csv") as distance_detail:
    distance = csv.reader(distance_detail, delimiter=',')
    distance = list(distance)


# This is the method to sort the package base on the distance, it take a dictionary as parameter.
# space and time complexity: O(N^2+N) ->O(N^2)
def sort_package_by_distance(truck):

    # add ID(0) to beginning of the truck, this mean that the truck start from hub, and there is no package of id 0.
    sorted_truck = {0: ['HUB', 0]}
    current_spot = "HUB"

    # go through each item in the unsorted truck and put them in the sorted_truck
    while len(truck) > 0:
         sorted_truck, current_spot = find_closest(current_spot, truck, sorted_truck)
    for address in address_name:
        if current_spot in address[1] or current_spot in address[0]:
            idx_of_last = address_name.index(address)

    # add ID(-1) to end of truck, this mean the truck return to hub in the end.
    sorted_truck[-1] = ['HUB', float(distance[idx_of_last][0])]
    return sorted_truck


# This is the algorithm to find the closet neighbor for current address. Once find the closet neighbor, it would return
# a dictionary which contain an array as value __ {key: ["x", "y"]} __ key is the package ID, 'x' is address, 'y' is
# distance to previous address, it would also return address of closet neighbor, which is used to find next closet neighbor.
# space and time complexity: O(N+N^2) ->O(N^2)
def find_closest(current, truck, package):
    distance_list = []   # an array to store distance to find min distance
    visited_address = ''  # a reference to store address info
    package_id = None   # a reference to store package id

    # find the index of current address in the address csv file
    for address in address_name:
        if current in address[1] or current in address[0]:
            idx_of_current = address_name.index(address)

    # find index of each package in the address csv file and find the response distance in distance csv file
    for i in truck:
        for address in address_name:
            if truck[i] in address[0] or truck[i] in address[1]:
                idx_of_list = address_name.index(address)
                if distance[idx_of_current][idx_of_list] is not '':
                    dst = distance[idx_of_current][idx_of_list]
                    distance_list.append(float(dst))
                    if min(distance_list) == float(distance[idx_of_current][idx_of_list]):
                        visited_address = truck[i]
                        package_id = i

                else:
                    dst = distance[idx_of_list][idx_of_current]
                    distance_list.append(float(dst))
                    if min(distance_list) == float(distance[idx_of_list][idx_of_current]):
                        visited_address = truck[i]
                        package_id = i
    current = visited_address
    package[package_id] = [visited_address, min(distance_list)]
    # once the closet address is found, delete it form original truck dictionary
    if truck[package_id] is not None:
        del truck[package_id]

    return package, current


# this method return address_name csv file
# space and time complexity: O(1)
def get_address():
    return address_name


# this method return distance csv file
# space and time complexity: O(1)
def get_distance():
    return distance


