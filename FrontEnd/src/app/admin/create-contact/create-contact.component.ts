import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Contact } from 'src/app/contact';
import { ContactService } from 'src/app/contact.service';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';

@Component({
  selector: 'create-contact',
  templateUrl: './create-contact.component.html',
  styleUrls: ['./create-contact.component.css']
})
export class CreateContactComponent {
  contact: Contact = new Contact();
  constructor(private service: ContactService, private router: Router) {}

  saveContact() {
    this.contact.address.id = NaN;
    this.service.createContact(this.contact).subscribe( response =>{
      if(response.status == 201) {
        this.router.navigate(['admin-table']);
      }
    },
    error => console.log(error));
  }

  onSubmit() {
    console.log(this.contact);
    this.saveContact();
  }
}
