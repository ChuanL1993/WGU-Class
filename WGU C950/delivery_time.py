import datetime


# This method take a hashtable, truck(dictionary), truck_ST(datetime) as parameter.
# It would ge the distance information from truck and get the float hours to complete that distance, and then pass into
# timedelta to find out delivered time for each package in the truck, and put the delivered time info in hashtable
# and return it.
#  space and time complexity: O(n^2)
def update_time(myhash, truck, truck_ST):
    current_time = truck_ST
    # key in truck is the ID number of package
    for x in truck.keys():
        hs = (truck.get(x)[1])/18
        time_spent = datetime.timedelta(hours= hs)
        current_time = current_time + time_spent
        if myhash.search(x) is not None:
            # add delivered time info to hashtable
            myhash.search(x).set_delivered(current_time)
    return myhash

