import { Injectable } from '@angular/core';
import { HttpClient} from "@angular/common/http";
import { Observable } from "rxjs";
import { Contact } from "./contact";

@Injectable({
  providedIn: 'root'
})
export class ContactService {

  constructor(private httpClient: HttpClient) { }

  getContactList(): Observable<Contact[]> {
    return this.httpClient.get<Contact[]>("/api/v1/contacts");
  }

  createContact(contact: Contact): Observable<Object> {
    return this.httpClient.post("/api/v1/contacts", contact);
  }

  getContactById(id: number): Observable<Contact> {
    return this.httpClient.get<Contact>(`/api/v1/contacts/${id}`);
  }

  updateContact(id: number, updatedContact: Contact): Observable<Object> {
    return this.httpClient.put(`/api/v1/contacts/${id}`, updatedContact);
  }
}
