from Package import Package


class HashTable:

    # Constructor with capacity of 10.
    # initial all bucket with an empty list.
    # space and time complexity: O(N)
    def __init__(self, initial_capacity=10):
        self.table = []
        for i in range(initial_capacity):
            self.table.append([])

    # insert a new key into hash table.
    # space and time complexity: O(N)
    def insert(self, key, item):
        bucket = hash(key) % len(self.table)
        bucket_list = self.table[bucket]

        # This part of code also work as update function
        for kv in bucket_list:
            if kv[0] == key:
                kv[1] = item
                return True

        key_value = [key, item]
        bucket_list.append(key_value)
        return True

    # define a search method with parameter key(int), method return value if the key is found, else return None
    # space and time complexity: O(N)
    def search(self, key):
        bucket = hash(key) % len(self.table)
        bucket_list = self.table[bucket]

        for kv in bucket_list:
            if kv[0] == key:
                return kv[1]
        return None

    # define a remove method with parameter key(int）， method would remove item from hashtable if key is found.
    # space and time complexity: O(N)
    def remove(self, key):
        bucket = hash(key) % len(self.table)
        bucket_list = self.table[bucket]

        for kv in bucket_list:
            if bucket_list[0] == key:
                bucket_list.remove([kv[0], kv[1]])