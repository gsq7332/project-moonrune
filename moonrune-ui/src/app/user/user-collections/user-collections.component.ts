import { Component } from '@angular/core';
import { MainRoutingComponent } from '../../general/main-routing/main-routing.component';
import { CollectionService } from '../../general/collection.service';
import { TermCollection } from '../../terms/termcollection';
import { NgFor, NgIf } from '@angular/common';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-user-collections',
  standalone: true,
  imports: [MainRoutingComponent, NgFor, NgIf],
  templateUrl: './user-collections.component.html',
  styleUrl: './user-collections.component.css'
})
export class UserCollectionsComponent {

  constructor(private collectionService: CollectionService, private router: ActivatedRoute) {}

  collections ?: TermCollection[]
  username: string = String(this.router.snapshot.paramMap.get('username'))

  ngOnInit() {
    this.collectionService.getCollectionsByOwner(this.username).subscribe(collections => this.collections = collections)
  }
}
