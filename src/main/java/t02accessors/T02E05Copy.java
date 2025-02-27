package t02accessors;

import java.io.IOException;

import helpers.GetResource;
import net.imglib2.Cursor;
import net.imglib2.RandomAccess;
import net.imglib2.img.Img;
import net.imglib2.img.cell.CellImgFactory;
import net.imglib2.img.display.imagej.ImageJFunctions;
import net.imglib2.loops.LoopBuilder;
import net.imglib2.type.numeric.ARGBType;

import ij.IJ;
import ij.ImagePlus;
import net.imglib2.view.Views;

/**
 * Copy one image to another using Cursor and RandomAccess.
 *
 * @author Tobias Pietzsch
 */
public class T02E05Copy
{
	public static void main( final String[] args ) throws IOException
	{
		final ImagePlus imp = IJ.openImage(GetResource.getFile("clown.png" ) );
//		final ImagePlus imp = IJ.openImage( "https://imagej.net/images/clown.png" );
		final Img< ARGBType > img = ImageJFunctions.wrap( imp );
		ImageJFunctions.show( img, "img" );

		final Img< ARGBType > copy = new CellImgFactory<>( new ARGBType() ).create( img );

		final Cursor< ARGBType > out = copy.localizingCursor();
		final RandomAccess< ARGBType > in = img.randomAccess();

		while( out.hasNext() )
		{
			out.fwd();
			in.setPosition( out );
			out.get().set( in.get() );
		}

//		LoopBuilder.setImages( img, copy ).forEachPixel( ( in, out ) -> out.set( in ) );

//		Views.interval( Views.pair( img, copy ), img ).forEach( p -> p.getB().set( p.getA() ) );

		ImageJFunctions.show( copy, "copy" );
	}
}
