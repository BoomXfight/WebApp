import {Component, OnInit} from '@angular/core';
import {Contact} from "../contact";
import {ContactService} from "../contact.service";
import {dateTimestampProvider} from "rxjs/internal/scheduler/dateTimestampProvider";
import {Router} from "@angular/router";

@Component({
  selector: 'app-create-contact',
  templateUrl: './create-contact.component.html',
  styleUrls: ['./create-contact.component.css']
})
export class CreateContactComponent implements OnInit {
  contact: Contact = new Contact()
  constructor(private contactService: ContactService,
              private router: Router) { }

  ngOnInit() { }

  saveContact(){
    this.contactService.createContact(this.contact).subscribe(data =>{
      console.log(data);
    })
    this.goToContactList();
  }

  goToContactList(){
    this.router.navigate(['contacts']);
  }
  onSubmit() {
    console.log(this.contact);
    this.saveContact();
  }

  protected readonly onsubmit = onsubmit;
}
