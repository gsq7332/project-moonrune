import { ComponentFixture, TestBed } from '@angular/core/testing';

import { OpenEndedBarComponent } from './open-ended-bar.component';

describe('OpenEndedBarComponent', () => {
  let component: OpenEndedBarComponent;
  let fixture: ComponentFixture<OpenEndedBarComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [OpenEndedBarComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(OpenEndedBarComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
