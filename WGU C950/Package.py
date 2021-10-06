
# Package class with constructor function, set_delivered, to_string, set_leave_hub functions.
class Package:

    # constructor
    # space and time complexity: O(1)
    def __init__(self, id, address, city, state, zip, deadline, mass, note, status):
        self.ID = id
        self.address = address
        self.city = city
        self.state = state
        self.zip = zip
        self.deadline = deadline
        self.mass = mass
        self.note = note
        self.status = status
        self.delivered = None  # time of package being delivered.
        self.leave_hub = None  # time of package leave the hub.

    # function to set delivered time, parameter: time(datetime).
    # space and time complexity: O(1)
    def set_delivered(self, time):
        self.delivered = time

    # function to set time when package leave the hub, parameter: leave_hub(datetime).
    # space and time complexity: O(1)
    def set_leave_hub(self, leave_hub):
        self.leave_hub = leave_hub

    # method for proper print data as string
    # space and time complexity: O(1)
    def __str__(self):
        return "%s, %s, %s, %s, %s, %s, %s, %s, %s. %s,%s" % (self.ID, self.address, self.city, self.state, self.zip, self.deadline,
                                                self.mass, self.note, self.status, self.delivered, self.leave_hub)

    # method to get address of package
    # space and time complexity: O(1)
    def get_address(self):
        return self.address


