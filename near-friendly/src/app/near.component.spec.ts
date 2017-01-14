/* tslint:disable:no-unused-variable */

import { TestBed, async } from '@angular/core/testing';
import { NearComponent } from './near.component';

describe('NearComponent', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [
        NearComponent
      ],
    });
    TestBed.compileComponents();
  });

  it('should create the app', async(() => {
    const fixture = TestBed.createComponent(NearComponent);
    const app = fixture.debugElement.componentInstance;
    expect(app).toBeTruthy();
  }));

  it(`should have as title 'Near view is now ! '`, async(() => {
    const fixture = TestBed.createComponent(NearComponent);
    const app = fixture.debugElement.componentInstance;
    expect(app.title).toEqual('Near view is now ! ');
  }));

  it('should render title in a h1 tag', async(() => {
    const fixture = TestBed.createComponent(NearComponent);
    fixture.detectChanges();
    const compiled = fixture.debugElement.nativeElement;
    expect(compiled.querySelector('h1').textContent).toContain('Near view is now ! ');
  }));
});
