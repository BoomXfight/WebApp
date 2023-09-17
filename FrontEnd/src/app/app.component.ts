import { Component } from '@angular/core';
import { KeycloakService } from 'keycloak-angular';
import { AdminComponent } from './admin/admin.component';
import { MatSlideToggleModule } from '@angular/material/slide-toggle';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss'],
})
export class AppComponent {
  role;
  isAdmin = false;
  isUser = false;

  constructor(private keycloakService: KeycloakService) {
    this.role = this.keycloakService.getUserRoles();
    this.isAdmin = this.role.includes('admin');
    this.isUser = this.role.includes('user');
  }
}