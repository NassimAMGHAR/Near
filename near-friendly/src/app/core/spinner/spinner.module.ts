import { NgModule, Optional, SkipSelf } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MaterialModule } from '@angular/material';

// import { throwIfAlreadyLoaded } from '../module-import-guard';
import { SpinnerComponent } from './spinner.component';
import { SpinnerService } from './spinner.service';

@NgModule({
  imports: [CommonModule, MaterialModule.forRoot()],
  exports: [SpinnerComponent],
  declarations: [SpinnerComponent],
  providers: [SpinnerService]
})
export class SpinnerModule {
  constructor( @Optional() @SkipSelf() parentModule: SpinnerModule) {
    //  throwIfAlreadyLoaded(parentModule, 'SpinnerModule')
  }
}
