import { Component } from '@angular/core';
import { RouterLink, RouterLinkActive, RouterOutlet } from '@angular/router';
import { TermServiceService } from './term-service.service';
import { TermCollection } from './terms/termcollection';
import { NgFor, NgIf } from '@angular/common';
import { SignOutComponent } from './user/sign-out/sign-out.component';
import { Cookie } from 'ng2-cookies';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [RouterOutlet, RouterLink, RouterLinkActive, NgFor, NgIf, SignOutComponent],
  templateUrl: './app.component.html',
  styleUrl: './app.component.css'
})
export class AppComponent {
  
  constructor(private termService: TermServiceService) {}

  title = 'Project Moonrune';
  presetCollections ?: TermCollection[]
  isSignedIn: boolean = false;
  username ?: string;

  ngOnInit() {
    this.termService.getCollectionsByOwner("Admin").subscribe(collections => this.presetCollections = collections)
    
  }

  checkSignedIn() {
    this.isSignedIn = Cookie.check("username")
    if (this.isSignedIn) this.username = Cookie.get("username")
  }
  
}
