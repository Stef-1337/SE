package de.ostfalia.s3.command;

public interface ICommand {

    void execute();
    ICommand undo();

}
