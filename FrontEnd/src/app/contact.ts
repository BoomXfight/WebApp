export class Contact {
  id: number = 0;
  firstName: string = "";
  lastName: string = "";
  email: string = "";
  phoneNumber: string = "";
  age: number = 0;
  address: {
      id: number;
      country: string;
      city: string;
      street: string;
      houseNumber: number;
  } = {
    id: 0,
    country: "",
    city: "",
    street: "",
    houseNumber: 0
  }
}
