import { Component } from '@angular/core';
import { TermsListComponent } from '../terms-list/terms-list.component';
import { ActivatedRoute, RouterLink, RouterLinkActive, RouterOutlet } from '@angular/router';
import { MainRoutingComponent } from '../../general/main-routing/main-routing.component';
import { switchMap } from 'rxjs';
import { NgIf } from '@angular/common';
import { TermsEditComponent } from '../terms-edit/terms-edit.component';
import { CollectionService } from '../../general/collection.service';
import { TermCollection } from '../../terms/termcollection';
import { Cookie } from 'ng2-cookies';
import { FilteringComponent } from '../../general/filtering/filtering.component';
import { filters } from '../../terms/filters';

@Component({
  selector: 'app-dictionary',
  standalone: true,
  imports: [TermsListComponent, RouterOutlet, RouterLink, RouterLinkActive, MainRoutingComponent, 
    NgIf, TermsEditComponent, FilteringComponent],
  templateUrl: './dictionary.component.html',
  styleUrl: './dictionary.component.css'
})
export class DictionaryComponent {
  constructor(private route: ActivatedRoute, private collectionService: CollectionService) {}

  id ?: number
  editMode: boolean = false;
  isOwner: boolean = false;
  hideFilterMenu : boolean = false;
  collectionInfo ?: TermCollection
  collectionFilter : filters = {
    matching: "",
      isDiacritic: 0,
      grades: [],
      jlpt: [],
      strokes: [0, 0],
      frequnecy: [0, 0]
  }
  appliedFilter : filters = this.collectionFilter;

  ngOnInit() {
    this.loadData()
  }

  loadData() {
    this.route.paramMap.pipe(
      switchMap(params => {
        this.id = Number(params.get('id'));
        return [];
      })
    ).subscribe();
    this.getCollection()
  }

  checkOwnership() {
    if (Cookie.check('username') && this.id != undefined) {
      let user = Cookie.get('username');
      this.collectionService.checkIfOwner(user, this.id).subscribe(ownership => this.isOwner = ownership)
    }
  }

  getTerms() {
    if (this.id == undefined) return;
    this.collectionService.getTermsWithFilter(this.id, this.collectionFilter);
  }

  getCollection() {
    if (this.id == undefined) return;
    this.collectionService.getCollectionInfo(this.id).subscribe(collectionInfo => {
      this.collectionInfo = collectionInfo
      this.checkOwnership()
  });
  }

  applyFilters() {
    this.appliedFilter = this.collectionFilter;
  }

  showFilters() {
    this.hideFilterMenu = false;
  }
 
  hideFilters() {
    this.hideFilterMenu = true;
  }
  
}
  
