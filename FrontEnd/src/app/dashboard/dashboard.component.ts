import { Component, OnInit } from '@angular/core';
import { KeycloakService } from 'keycloak-angular';

@Component({
  selector: 'dashboard-component',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css']
})
export class DashboardComponent implements OnInit {
  
  isAdmin = false;

  constructor(private keycloakService: KeycloakService) {}
  logout() {
    this.keycloakService.logout();
  }

  ngOnInit(): void {
    const roles = this.keycloakService.getUserRoles(); // This method depends on your implementation
    this.isAdmin = roles.includes('admin');
  }
}
