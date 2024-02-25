import { Component, Input, AfterViewInit, ElementRef, ViewChild } from '@angular/core';

declare var FB: any;

@Component({
  selector: 'facebook-share',
  template: `
    <div #div
         class="fb-share-button"
         [attr.data-href]="url"
         data-layout="button"
         data-size="small"
         data-mobile-iframe="true"
         ><a class="fb-xfbml-parse-ignore"
             target="_blank"
             href="https://www.facebook.com/sharer/sharer.php?u={{url | encode}}&amp;src=sdkpreparse"
             >Share</a></div>
  `,
})
export class FacebookShareComponent implements AfterViewInit {
  @ViewChild('div') div: ElementRef;
  @Input() url: string;

  ngAfterViewInit() {
    FB.XFBML.parse(this.div.nativeElement.parentNode);
  }
}
