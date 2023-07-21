export class Contact {
    id: number;
    firstName: string;
    lastName: string;
    email: string;
    age: number;
    phoneNumber: string;
    address: string;

    constructor() {
        this.id = 0;
        this.firstName = '';
        this.lastName = '';
        this.email = '';
        this.age = 0;
        this.phoneNumber = '';
        this.address = '';
    }
}
