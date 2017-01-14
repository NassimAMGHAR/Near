import { NearFriendlyPage } from './app.po';

describe('near-friendly App', function() {
  let page: NearFriendlyPage;

  beforeEach(() => {
    page = new NearFriendlyPage();
  });

  it('should display message saying app works', () => {
    page.navigateTo();
    expect(page.getParagraphText()).toEqual('app works!');
  });
});
