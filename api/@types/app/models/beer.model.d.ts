export type BeerCreate = {
    name: string,
    barcode: number,
    type: 'Lager' | 'Hazy',
    photoUrl: string | undefined
}
