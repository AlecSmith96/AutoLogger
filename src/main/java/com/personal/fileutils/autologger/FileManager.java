package com.personal.fileutils.autologger;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

@Component
@Slf4j
public class FileManager
{
    @Value("${logger.fileLocation}")
    private String fileLocation;

    @Value("${logger.logCapitalized}")
    private boolean logCapitalized;

    public void processFiles()
    {
        File folder = new File(fileLocation);
        File[] listOfFiles = folder.listFiles();
        Arrays.stream(listOfFiles).forEach(file -> {
            if (!file.isDirectory())
            {
                try
                {
                    log.info("Replacing statements in file " + file.getName());
                    replaceStatementsForFile(file);
                }
                catch (IOException e)
                {
                    log.error("Unable to replacement statements for file " + file.getName(), e);
                }
            }
        });
    }

    private void replaceStatementsForFile(File file) throws IOException
    {
        Path path = Paths.get(file.getPath());
        Charset charset = StandardCharsets.UTF_8;
        String content = getFileContent(file, path, charset);
        content = replaceStatements(content);
        try
        {
            Files.write(path, content.getBytes(charset));
        } catch (IOException e)
        {
            log.error("Unable to write new content to file " + file.getName(), e);
        }
    }

    private String replaceStatements(String content)
    {
        if (logCapitalized)
        {
            content = content.replaceAll("System.out.println", "LOG.logInfo");
            content = content.replaceAll("System.err.println", "LOG.logError");
            content = content.replaceAll("[^\\s]*.printStackTrace[(][)]", "LOG.logError(e)");           //need to find a way to pass exception name to logger correctly
            return content;
        }
        content = content.replaceAll("System.out.println", "log.logInfo");
        content = content.replaceAll("System.err.println", "log.logError");
        content = content.replaceAll("[^\\s]*.printStackTrace[(][)]", "log.logError(e)");                  //need to find a way to pass exception name to logger correctly
        return content;
    }

    private String getFileContent(File file, Path path, Charset charset) throws IOException
    {
        try
        {
            return new String(Files.readAllBytes(path), charset);
        } catch (IOException e)
        {
            log.error("Unable to read content of file " + file.getName(), e);
        }
        throw new IOException();
    }
}
