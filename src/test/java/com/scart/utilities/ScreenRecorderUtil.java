package com.scart.utilities;
import java.awt.AWTException;
import java.awt.Dimension;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsEnvironment;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
 
import org.monte.media.Format;
import org.monte.media.Registry;
import org.monte.screenrecorder.ScreenRecorder;
import org.monte.media.math.Rational;
import org.monte.media.FormatKeys.MediaType;
 
import static org.monte.media.AudioFormatKeys.*;
import static org.monte.media.VideoFormatKeys.*;
 
import org.bytedeco.javacv.*;
import org.bytedeco.ffmpeg.global.avcodec;
 
public class ScreenRecorderUtil extends ScreenRecorder {
    public static ScreenRecorder screenRecorder;
    public String name;
    private File movieFolder;
 
    public ScreenRecorderUtil(GraphicsConfiguration cfg, Rectangle captureArea, Format fileFormat,
                              Format screenFormat, Format mouseFormat, Format audioFormat, File movieFolder, String name)
            throws IOException, AWTException {
        super(cfg, captureArea, fileFormat, screenFormat, mouseFormat, audioFormat, movieFolder);
        this.name = name;
        this.movieFolder = movieFolder;
    }
 
    @Override
    protected File createMovieFile(Format fileFormat) throws IOException {
        if (!movieFolder.exists()) {
            movieFolder.mkdirs();
        } else if (!movieFolder.isDirectory()) {
            throw new IOException("\"" + movieFolder + "\" is not a directory.");
        }
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH.mm.ss");
        return new File(movieFolder,
                name + "-" + dateFormat.format(new Date()) + "." + Registry.getInstance().getExtension(fileFormat));
    }
 
    public static void startRecord(String methodName) throws Exception {
    	//Added property from system now for consistency
        File file = new File(System.getProperty("user.dir"),"Test-Recordings/");
        if (!file.exists()) {
            file.mkdirs(); 
        }
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int width = screenSize.width;
        int height = screenSize.height;
 
        Rectangle captureSize = new Rectangle(0, 0, width, height);
 
        GraphicsConfiguration gc = GraphicsEnvironment.getLocalGraphicsEnvironment()
                .getDefaultScreenDevice()
                .getDefaultConfiguration();
 
        screenRecorder = new ScreenRecorderUtil(gc, captureSize,
                new Format(MediaTypeKey, MediaType.FILE, MimeTypeKey, MIME_AVI),
                new Format(MediaTypeKey, MediaType.VIDEO, EncodingKey, ENCODING_AVI_TECHSMITH_SCREEN_CAPTURE,
                        CompressorNameKey, ENCODING_AVI_TECHSMITH_SCREEN_CAPTURE, DepthKey, 24, FrameRateKey,
                        Rational.valueOf(15), QualityKey, 1.0f, KeyFrameIntervalKey, 15 * 60),
                new Format(MediaTypeKey, MediaType.VIDEO, EncodingKey, "black", FrameRateKey, Rational.valueOf(30)),
                null, file, methodName);
        screenRecorder.start();
    }
 
    public static void stopRecord() throws Exception {
        screenRecorder.stop();
        File source = screenRecorder.getCreatedMovieFiles().get(0);
        File target = new File(source.getParent(), source.getName().replace(".avi", ".mp4"));
        convertToMp4(source, target);
    }
 
    private static void convertToMp4(File source, File target) throws Exception {
        FFmpegFrameGrabber grabber = new FFmpegFrameGrabber(source);
        grabber.start();
 
        FFmpegFrameRecorder recorder = new FFmpegFrameRecorder(target, grabber.getImageWidth(), grabber.getImageHeight(), grabber.getAudioChannels());
        recorder.setVideoCodec(avcodec.AV_CODEC_ID_H264);
        recorder.setFormat("mp4");
        recorder.setFrameRate(grabber.getFrameRate());
        recorder.setAudioCodec(avcodec.AV_CODEC_ID_AAC);
        recorder.start();
 
        Frame frame;
        while ((frame = grabber.grab()) != null) {
            recorder.record(frame);
        }
 
        recorder.stop();
        grabber.stop();
 
      //  logger.info("Video converted successfully to MP4: " + target.getAbsolutePath());
    }
}