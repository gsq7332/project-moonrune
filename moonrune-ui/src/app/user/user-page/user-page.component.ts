import { Component } from '@angular/core';
import { User } from '../user';
import { UserService } from '../user.service';
import { ActivatedRoute } from '@angular/router';
import { NgIf } from '@angular/common';
import { MainRoutingComponent } from '../../general/main-routing/main-routing.component';

@Component({
  selector: 'app-user-page',
  standalone: true,
  imports: [NgIf, MainRoutingComponent],
  templateUrl: './user-page.component.html',
  styleUrl: './user-page.component.css'
})
export class UserPageComponent {

  user ?: User
  username ?: string = String(this.route.snapshot.paramMap.get("username"))

  constructor(private userService: UserService, private route: ActivatedRoute) {}

  ngOnInit() {
    if (this.username == undefined) return;
    this.userService.getUser(this.username).subscribe(user => this.user = user)
  }

}
