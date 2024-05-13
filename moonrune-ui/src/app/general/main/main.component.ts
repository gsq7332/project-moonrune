import { NgIf } from '@angular/common';
import { Component } from '@angular/core';
import { Cookie } from 'ng2-cookies';

@Component({
  selector: 'app-main',
  standalone: true,
  imports: [NgIf],
  templateUrl: './main.component.html',
  styleUrl: './main.component.css'
})
export class MainComponent {

  username ?: string;

  ngOnInit() {
    if (Cookie.check("username")) {
      this.username = Cookie.get("username");
    }
  }
}
