import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'encode'
})
export class EncodePipe implements PipeTransform {
  transform(content: string): string {
    return encodeURIComponent(content);
  }
}

