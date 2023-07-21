import { Component } from '@angular/core';
import { Contact } from '../contact'
import { ContactService } from "../contact.service";
import {Router} from "@angular/router";

@Component({
  selector: 'app-contact-list',
  templateUrl: './contact-list.component.html',
  styleUrls: ['./contact-list.component.css']
})
export class ContactListComponent {

  contacts: Contact[] = [];

  constructor(private contactService: ContactService, private router: Router) { }

  private getContacts(){
    this.contactService.getContactList().subscribe(data =>{
      this.contacts = data;
    })
  }

  ngOnInit() : void {
    this.getContacts();
  }

  updateContact(id: number){
    this.router.navigate(['update-contact', id]);
  }

}
