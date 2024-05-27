import { Component, Input } from '@angular/core';
import { TermsListComponent } from '../list-view/terms-list/terms-list.component';
import { ActivatedRoute, RouterLink, RouterLinkActive, RouterOutlet } from '@angular/router';
import { MainRoutingComponent } from '../../general/main-routing/main-routing.component';
import { switchMap } from 'rxjs';
import { NgIf } from '@angular/common';
import { TermsEditComponent } from '../terms-edit/terms-edit.component';
import { CollectionService } from '../../general/collection.service';
import { TermCollection } from '../../terms/termcollection';

@Component({
  selector: 'app-dictionary',
  standalone: true,
  imports: [TermsListComponent, RouterOutlet, RouterLink, RouterLinkActive, MainRoutingComponent, NgIf, TermsEditComponent],
  templateUrl: './dictionary.component.html',
  styleUrl: './dictionary.component.css'
})
export class DictionaryComponent {
  constructor(private route: ActivatedRoute, private collectionService: CollectionService) {}

  id ?: number
  editMode = false;
  isOwner: boolean = false;
  collectionInfo ?: TermCollection

  ngOnInit() {
    this.route.paramMap.pipe(
      switchMap(params => {
        this.id = Number(params.get('id'));
        return [];
      })
    ).subscribe();
    this.checkOwnership()
  }

  checkOwnership() {
    
  }

  getCollection() {
    
  }

  
}
  
