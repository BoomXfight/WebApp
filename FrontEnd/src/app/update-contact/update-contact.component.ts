import { Component } from '@angular/core';
import {Contact} from "../contact";
import {ContactService} from "../contact.service";
import {Router} from "@angular/router";

@Component({
  selector: 'app-update-contact',
  templateUrl: './update-contact.component.html',
  styleUrls: ['./update-contact.component.css']
})
export class UpdateContactComponent {
  contact: Contact = new Contact()

  constructor(private contactService: ContactService,
              private router: Router) { }

  onSubmit() {
    console.log(this.contact);
  }
}
