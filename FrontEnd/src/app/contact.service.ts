import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse} from "@angular/common/http";
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

  createContact(contact: Contact): Observable<HttpResponse<string>> {
    return this.httpClient.post<string>("/api/v1/contacts", contact, { observe: 'response', responseType: 'text' as 'json' });
  }

  getContactById(id: number): Observable<Contact> {
    return this.httpClient.get<Contact>(`/api/v1/contacts/${id}`);
  }

  updateContact(id: number, updatedContact: Contact): Observable<HttpResponse<string>> {
    return this.httpClient.put<string>(`/api/v1/contacts/${id}`, updatedContact, { observe: 'response', responseType: 'text' as 'json'});
  }

  deleteContact(id: number): Observable<Object> {
    return this.httpClient.delete(`/api/v1/contacts/${id}`);
  }
}
