import { Component } from '@angular/core';
import { RouterLink, RouterLinkActive, RouterOutlet } from '@angular/router';
import { TermServiceService } from './term-service.service';
import { TermCollection } from './general/collection';
import { NgFor } from '@angular/common';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [RouterOutlet, RouterLink, RouterLinkActive, NgFor],
  templateUrl: './app.component.html',
  styleUrl: './app.component.css'
})
export class AppComponent {
  
  constructor(private termService: TermServiceService) {}

  title = 'Project Moonrune';
  presetCollections ?: TermCollection[]

  ngOnInit() {
    this.termService.getCollectionsByOwner("Admin").subscribe(collections => this.presetCollections = collections)
  }
  
}
