export interface Contact {
  id: number;
  firstName: string;
  lastName: string;
  email: string;
  phoneNumber: string;
  age: number;
  address: {
      id: number;
      country: string;
      city: string;
      street: string;
      houseNumber: number;
  }
}
