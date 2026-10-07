package seedu.address.testutil;

import seedu.address.model.person.Email;
import seedu.address.model.person.EventDate;
import seedu.address.model.person.EventType;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;

/**
 * A utility class to help with building Person objects.
 */
public class PersonBuilder {

    public static final String DEFAULT_NAME = "Amy Bee";
    public static final String DEFAULT_PHONE = "85355255";
    public static final String DEFAULT_EMAIL = "amy@gmail.com";
    public static final String DEFAULT_EVENT_TYPE = "Wedding";
    public static final String DEFAULT_EVENT_DATE = "2026-12-01";

    private Name name;
    private Phone phone;
    private Email email;
    private EventType eventType;
    private EventDate eventDate;

    /**
     * Creates a {@code PersonBuilder} with the default details.
     */
    public PersonBuilder() {
        name = new Name(DEFAULT_NAME);
        phone = new Phone(DEFAULT_PHONE);
        email = new Email(DEFAULT_EMAIL);
        eventType = new EventType(DEFAULT_EVENT_TYPE);
        eventDate = new EventDate(DEFAULT_EVENT_DATE);
    }

    /**
     * Initializes the PersonBuilder with the data of {@code personToCopy}.
     */
    public PersonBuilder(Person personToCopy) {
        name = personToCopy.getName();
        phone = personToCopy.getPhone();
        email = personToCopy.getEmail();
        eventType = personToCopy.getEventType();
        eventDate = personToCopy.getEventDate();
    }

    /**
     * Sets the {@code Name} of the {@code Person} that we are building.
     */
    public PersonBuilder withName(String name) {
        this.name = new Name(name);
        return this;
    }

    /**
     * Sets the {@code EventType} of the {@code Person} that we are building.
     */
    public PersonBuilder withEventType(String eventType) {
        this.eventType = new EventType(eventType);
        return this;
    }

    /**
     * Sets the {@code EventDate} of the {@code Person} that we are building.
     */
    public PersonBuilder withEventDate(String eventDate) {
        this.eventDate = new EventDate(eventDate);
        return this;
    }

    /**
     * Sets the {@code Phone} of the {@code Person} that we are building.
     */
    public PersonBuilder withPhone(String phone) {
        this.phone = new Phone(phone);
        return this;
    }

    /**
     * Sets the {@code Email} of the {@code Person} that we are building.
     */
    public PersonBuilder withEmail(String email) {
        this.email = new Email(email);
        return this;
    }

    public Person build() {
        return new Person(name, phone, email, eventType, eventDate);
    }

}
