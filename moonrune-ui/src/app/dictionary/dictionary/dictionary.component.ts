import { Component, Input } from '@angular/core';
import { TermsListComponent } from '../list-view/terms-list/terms-list.component';
import { ActivatedRoute, RouterLink, RouterLinkActive, RouterOutlet } from '@angular/router';
import { MainRoutingComponent } from '../../general/main-routing/main-routing.component';
import { switchMap } from 'rxjs';
import { NgIf } from '@angular/common';

@Component({
  selector: 'app-dictionary',
  standalone: true,
  imports: [TermsListComponent, RouterOutlet, RouterLink, RouterLinkActive, MainRoutingComponent, NgIf],
  templateUrl: './dictionary.component.html',
  styleUrl: './dictionary.component.css'
})
export class DictionaryComponent {
  constructor(private route: ActivatedRoute) {}

  id ?: number
  editMode = false;


  ngOnInit() {
    this.route.paramMap.pipe(
      switchMap(params => {
        this.id = Number(params.get('id'));
        return [];
      })
    ).subscribe();
  }

  startEdit() {
    this.editMode = true;
  }

  cancelEdit() {
    this.editMode = false;
  }

  confirmEdit() {
    this.editMode = false;
  }
}
  
