import { NgFor, NgIf } from '@angular/common';
import { Component, Input } from '@angular/core';
import { RouterLink, RouterLinkActive, RouterOutlet } from '@angular/router';
import { SignOutComponent } from '../../user/sign-out/sign-out.component';
import { TermCollection } from '../../terms/termcollection';
import { Cookie } from 'ng2-cookies';
import { CollectionService } from '../collection.service';

@Component({
  selector: 'app-main-routing',
  standalone: true,
  imports: [RouterOutlet, RouterLink, RouterLinkActive, NgFor, NgIf, SignOutComponent],
  templateUrl: './main-routing.component.html',
  styleUrl: './main-routing.component.css'
})
export class MainRoutingComponent {

  constructor(private termService: CollectionService) {}
  presetCollections ?: TermCollection[]
  isSignedIn: boolean = false;
  username ?: string;
  @Input() isDictionary ?: boolean

  ngOnInit() {
    this.termService.getCollectionsByOwner("Admin").subscribe(collections => this.presetCollections = collections)
    this.checkSignedIn()
    
  }

  checkSignedIn() {
    this.isSignedIn = Cookie.check("username")
    if (this.isSignedIn) this.username = Cookie.get("username")
  }

  refreshIfDictionary() {
    console.log('a')
    if (this.isDictionary == undefined) return
    if (this.isDictionary) window.location.reload()
    console.log('refreshed')
  }
}
