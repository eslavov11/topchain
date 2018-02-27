import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { NetworkDifficultyComponent } from './network-difficulty.component';

describe('NetworkDifficultyComponent', () => {
  let component: NetworkDifficultyComponent;
  let fixture: ComponentFixture<NetworkDifficultyComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ NetworkDifficultyComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(NetworkDifficultyComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
