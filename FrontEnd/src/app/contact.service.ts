import { Injectable } from '@angular/core';
import { HttpClient} from "@angular/common/http";
import { Observable } from "rxjs";
import { Contact } from "./contact";

@Injectable({
  providedIn: 'root'
})
export class ContactService {

  private baseURL= "http://localhost:8080/api/v1/contacts";
  constructor(private httpClient: HttpClient) { }

  getContactList(): Observable<Contact[]> {
    return this.httpClient.get<Contact[]>(`${this.baseURL}`);
  }

  createContact(contact: Contact): Observable<Object> {
    return this.httpClient.post(`${this.baseURL}`, contact);
  }

  getContactById(id: number): Observable<Contact> {
    return this.httpClient.get<Contact>(`${this.baseURL}/${id}`);
  }

  updateContact(id: number, updatedContact: Contact): Observable<Object> {
    return this.httpClient.put(`${this.baseURL}/${id}`, updatedContact);
  }
}
